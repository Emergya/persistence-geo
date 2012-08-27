INSERT INTO persistence_geo."usuario"(
            user_id, username)
    VALUES (1, '${default_admin_user}');
INSERT INTO persistence_geo.usuarios_por_institucion(
            authority_role_id, user_id)
    VALUES (13, 1);