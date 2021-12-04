package com.example.springproj11;

import net.andreinc.mockneat.MockNeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class AddressController {

    @Autowired
    AddressRepository addressRepository;

    @PostMapping("/createaddress")
    public ResponseEntity<Address> createAddress(@RequestParam String name, @RequestParam String address_line, @RequestParam Double lat, @RequestParam Double lon) {
        try {
            Address address = new Address(name, address_line, lat, lon);
            Address _address = addressRepository.save(address);
            assert _address != null;
            return ResponseEntity.status(HttpStatus.CREATED).body(_address);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/updatebyname")
    public ResponseEntity<Address> updateAddressByName(@RequestParam String name, @RequestParam String newAddress) {
        try {
            Address address = addressRepository.findByName(name);
            address.setAddressLine(newAddress);
            Address _address = addressRepository.save(address);
            return ResponseEntity.status(HttpStatus.CREATED).body(_address);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/updatebyid")
    public ResponseEntity<Address> updateAddressByID(@RequestParam UUID id, @RequestParam String newAddress) {
        try {
            Address address = addressRepository.findById(id);
            address.setAddressLine(newAddress);
            Address _address = addressRepository.save(address);
            return ResponseEntity.status(HttpStatus.CREATED).body(_address);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Generate a line with id,name,newAddress and append to csv file
    @PostMapping("/mockfortest")
    public String mockName(@RequestParam String name) throws IOException {
        MockNeat m = MockNeat.threadLocal();

        Address address = addressRepository.findByName(name);
        UUID id = address.getId();

        String line = String.format("%s,%s,%s", id, name, m.addresses().line1().get());
        FileWriter writer = new FileWriter("mock_data_update.csv",true);
        writer.write("\n");
        writer.write(line);
        writer.close();
        return String.format("Updated mock_data_update.csv with %s", line);
    }

    @GetMapping("/mockforcreate")
    public String mockData() throws IOException {
        MockNeat m = MockNeat.threadLocal();

        List<String> strings = m.fmt("#{name},#{addressline},#{lat},#{lon}")
                .param("name", m.names())
                .param("addressline", m.addresses())
                .param("lat", m.doubles())
                .param("lon", m.doubles())
                .list(100000)
                .val();
        BufferedWriter writer = new BufferedWriter(new FileWriter("mock_data_addresses.csv"));
        writer.write("name,address_line,lat,lon");
        assert strings != null;
        for (String line : strings) {
            writer.write("\n");
            writer.write(line);
        }
        writer.close();

        return "Created mock_data_addresses.csv";
    }
}
