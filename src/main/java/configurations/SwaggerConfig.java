package configurations;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

@OpenAPIDefinition(
    info = @Info(
        title = "API de Obra Social",
        version = "1.0.0",
        contact = @Contact(
            name = "Equipo 1",
            email = "equipo1@gmail.com",
            url = "http://www.google.com"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html"
        )
    )
)
@ApplicationPath("/api")
public class SwaggerConfig extends Application {
}