package pe.edu.upc.opennova.automovilunite.iam.domain.model.commands;

import pe.edu.upc.opennova.automovilunite.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String username, String password, List<Role> roles) {
}
