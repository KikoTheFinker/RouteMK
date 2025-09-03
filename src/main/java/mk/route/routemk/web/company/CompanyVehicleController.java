package mk.route.routemk.web.company;

import mk.route.routemk.models.Driver;
import mk.route.routemk.models.DriverVehicleOperation;
import mk.route.routemk.models.TransportOrganizer;
import mk.route.routemk.models.Vehicle;
import mk.route.routemk.services.auth.interfaces.AuthenticationService;
import mk.route.routemk.services.interfaces.DriverService;
import mk.route.routemk.services.interfaces.DriverVehicleOperationService;
import mk.route.routemk.services.interfaces.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vehicles")
public class CompanyVehicleController {

    private final VehicleService vehicleService;
    private final DriverService driverService;
    private final DriverVehicleOperationService dvoService;
    private final AuthenticationService authenticationService;

    public CompanyVehicleController(VehicleService vehicleService,
                                    DriverService driverService,
                                    DriverVehicleOperationService dvoService,
                                    AuthenticationService authenticationService) {
        this.vehicleService = vehicleService;
        this.driverService = driverService;
        this.dvoService = dvoService;
        this.authenticationService = authenticationService;
    }

    private TransportOrganizer requireOrganizer() {
        var accOpt = authenticationService.getAuthenticatedAccount();
        if (accOpt.isEmpty() || accOpt.get().getTransportOrganizer() == null) {
            throw new IllegalStateException("Transport Organizer session required.");
        }
        return accOpt.get().getTransportOrganizer();
    }

    private String organizerCompanyName() {
        var acc = authenticationService.getAuthenticatedAccount()
                .orElseThrow(() -> new IllegalStateException("Transport Organizer session required."));
        return acc.getTransportOrganizer().getCompanyName();
    }

    private Map<Integer, String> buildDriverDisplay(List<Driver> drivers) {
        Map<Integer, String> driverDisplay = new HashMap<>();
        for (Driver d : drivers) {
            String first = (d.getAccount() != null && d.getAccount().getName() != null)
                    ? d.getAccount().getName() : "";
            String last = (d.getAccount() != null && d.getAccount().getSurname() != null)
                    ? d.getAccount().getSurname() : "";
            String full = (first + " " + last).trim();
            if (full.isEmpty()) full = "Driver #" + d.getDriverId();
            driverDisplay.put(d.getDriverId(), full);
        }
        return driverDisplay;
    }

    @GetMapping
    public String list(Model model) {
        TransportOrganizer org = requireOrganizer();
        var vehicles = vehicleService.findByTranOrg(org);

        var allOps = dvoService.findAll();
        Map<Integer, List<Driver>> driversPerVehicle = new HashMap<>();
        for (Vehicle v : vehicles) {
            List<Driver> assigned = allOps.stream()
                    .filter(op -> Objects.equals(op.getVehicleId(), v.getVehicleId()))
                    .map(DriverVehicleOperation::getDriver)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            driversPerVehicle.put(v.getVehicleId(), assigned);
        }

        model.addAttribute("vehicles", vehicles);
        model.addAttribute("driversPerVehicle", driversPerVehicle);
        model.addAttribute("companyName", organizerCompanyName());
        model.addAttribute("display", "company/vehicles-list");
        return "master";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("title", "Register a Vehicle");
        model.addAttribute("companyName", organizerCompanyName());
        model.addAttribute("display", "company/vehicle-form");
        return "master";
    }

    @PostMapping
    public String create(@RequestParam String brand,
                         @RequestParam String modelName,
                         @RequestParam String capacity,
                         @RequestParam(required = false) String yearManufactured,
                         RedirectAttributes ra) {
        TransportOrganizer org = requireOrganizer();

        Vehicle v = new Vehicle();
        v.setTranOrgId(org.getTranOrgId());
        v.setBrand(brand);
        v.setModel(modelName);
        v.setCapacity(capacity);
        v.setYearManufactured(yearManufactured);

        vehicleService.save(v);
        ra.addFlashAttribute("infoMessage", "Vehicle registered.");
        return "redirect:/vehicles";
    }

    @GetMapping("/{vehicleId}/edit")
    public String editForm(@PathVariable Integer vehicleId, Model model) {
        Vehicle v = vehicleService.findById(vehicleId);
        model.addAttribute("vehicle", v);
        model.addAttribute("title", "Edit Vehicle");
        model.addAttribute("companyName", organizerCompanyName());
        model.addAttribute("display", "company/vehicle-form");
        return "master";
    }

    @PostMapping("/{vehicleId}")
    public String update(@PathVariable Integer vehicleId,
                         @RequestParam String brand,
                         @RequestParam String modelName,
                         @RequestParam String capacity,
                         @RequestParam(required = false) String yearManufactured,
                         RedirectAttributes ra) {
        Vehicle v = vehicleService.findById(vehicleId);
        v.setBrand(brand);
        v.setModel(modelName);
        v.setCapacity(capacity);
        v.setYearManufactured(yearManufactured);
        vehicleService.save(v);
        ra.addFlashAttribute("infoMessage", "Vehicle updated.");
        return "redirect:/vehicles";
    }

    @PostMapping("/{vehicleId}/delete")
    public String delete(@PathVariable Integer vehicleId, RedirectAttributes ra) {
        vehicleService.deleteById(vehicleId);
        ra.addFlashAttribute("infoMessage", "Vehicle deleted.");
        return "redirect:/vehicles";
    }

    @GetMapping("/{vehicleId}/assign")
    public String assignForm(@PathVariable Integer vehicleId, Model model) {
        TransportOrganizer org = requireOrganizer();

        Vehicle vehicle = vehicleService.findById(vehicleId);
        List<Driver> drivers = driverService.findByTranOrg(org);

        Set<Integer> assignedDriverIds = dvoService.findAll().stream()
                .filter(op -> Objects.equals(op.getVehicleId(), vehicleId))
                .map(DriverVehicleOperation::getDriverId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("drivers", drivers);
        model.addAttribute("assignedDriverIds", assignedDriverIds);
        model.addAttribute("driverDisplay", buildDriverDisplay(drivers));
        model.addAttribute("companyName", organizerCompanyName());
        model.addAttribute("display", "company/vehicle-assign-drivers");
        return "master";
    }

    @PostMapping("/{vehicleId}/assign")
    public String assign(@PathVariable Integer vehicleId,
                         @RequestParam(name = "driverIds", required = false) List<Integer> driverIds,
                         RedirectAttributes ra) {
        Set<Integer> requested = (driverIds == null) ? Collections.emptySet() : new HashSet<>(driverIds);

        var allOps = dvoService.findAll();
        var currentForVehicle = allOps.stream()
                .filter(op -> Objects.equals(op.getVehicleId(), vehicleId))
                .toList();

        Set<Integer> current = currentForVehicle.stream()
                .map(DriverVehicleOperation::getDriverId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        for (DriverVehicleOperation op : currentForVehicle) {
            if (!requested.contains(op.getDriverId())) {
                dvoService.deleteById(op.getId());
            }
        }
        for (Integer reqId : requested) {
            if (!current.contains(reqId)) {
                dvoService.save(new DriverVehicleOperation(reqId, vehicleId));
            }
        }

        ra.addFlashAttribute("infoMessage", "Assignments updated.");
        return "redirect:/vehicles";
    }
}
