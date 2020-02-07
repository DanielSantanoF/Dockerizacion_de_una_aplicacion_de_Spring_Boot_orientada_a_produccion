insert into CENTRO (id,ccaa,localidad,nombre,provincia) values (1,'Andalucía', 'Sevilla','Salesianos de Triana','Sevilla');
insert into ESPACIO (id, alumnos, nombre, puestos, centro_id) values(1,30,'1ºDAM',30,1);

insert into USUARIO (id, activo, admin, email, fechaalta, password, registro_confirmado, superadmin, username,centro_id) values (1, true, true, 'jallamas@yo.es', null, '1234', true, true, 'José Antonio Llamas',1);
insert into USUARIO (id, activo, admin, email, fechaalta, password, registro_confirmado, superadmin, username,centro_id) values (2, true, false, 'santano@yo.es', null, '1234', false, false, 'Daniel Santano',1);
insert into USUARIO (id, activo, admin, email, fechaalta, password, registro_confirmado, superadmin, username,centro_id) values (3, false, false, 'lazaro@yo.es', null, '1234', true, false, 'Miguel Lázaro',1);
insert into RESERVA (id, fecha, hora, espacio_id, usuario_id) values (1,TO_DATE('23/06/2019','DD/MM/YYYY'), CAST ('12:05:06.0000000' AS TIME),1,1);
 
