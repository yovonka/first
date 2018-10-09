package jaxb;

import com.bharatthippireddy.patient.Patient;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;
import java.util.function.Supplier;

public class JAXBTrainee<T extends Patient> {

    private Supplier<T> supplier;
    private static final StringWriter WRITER = new StringWriter();

    public JAXBTrainee(Supplier<T> supplier) {
        this.supplier = Objects.requireNonNull(supplier);
    }

    public void train() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(supplier.get().getClass());
            tryMarshalling(jaxbContext);
            tryUnmarshalling(jaxbContext);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void tryUnmarshalling(JAXBContext jaxbContext) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader stringReader = new StringReader(WRITER.toString());
        Patient person = (Patient) unmarshaller.unmarshal(stringReader);
        System.out.println(person.getName());
    }

    private void tryMarshalling(JAXBContext jaxbContext) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(getPerson(), WRITER);
        System.out.println(WRITER);
    }

    private T getPerson() {
        T t = supplier.get();
        t.setId(12345);
        t.setName("Kay");
        return t;
    }
}
