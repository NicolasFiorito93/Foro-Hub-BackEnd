alter table respuestas add borrado tinyint;
update respuestas set borrado = 1;