package pe.edu.upc.opennova.automovilunite.iam.domain.services;


import pe.edu.upc.opennova.automovilunite.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
  void handle(SeedRolesCommand command);
}
