package pe.edu.upc.opennova.automovilunite;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJpaAuditing

public class AutomovilUniteApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomovilUniteApplication.class, args);
	}

}
