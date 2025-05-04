package Elkhadema.khadema.Service.ServiceInterfaces;

import java.io.IOException;

import Elkhadema.khadema.domain.Person;

public interface GenerateCVService {
    void generateCV(Person person) throws IOException;
}
