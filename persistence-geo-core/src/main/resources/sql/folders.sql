INSERT INTO folder(
            id, enabled, is_channel, is_plain, update_date, create_date, 
            name, folder_user_id)
    VALUES (1, true, true, false, null, null, 
            '${default_admin_user} folder', 1)
INSERT INTO folder(
            id, enabled, is_channel, is_plain, update_date, create_date, 
            name, folder_auth_id)
    VALUES (2, true, true, false, null, null, 
            'admin folder', 1)