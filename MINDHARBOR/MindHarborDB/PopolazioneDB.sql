
-- POPOLAZIONE UTENTE

insert into Utente values ('Ros98','123','Claudia','Rossi','Paziente');
insert into Utente values ('Ciccio','100','Alessio','Matteini','Psicologo');
insert into Utente values ('Giacomino','1998','Giacomo','Polidori','Paziente');
insert into Utente values ('BelPaolino','Q8TT-23!','Paolino','Perfettino','Psicologo');
insert into Utente values ('IlPrincipe','JDK90?','Carlo','Terzo','Psicologo');
insert into Utente values ('Luigi@67','<pallino>','Luigi','Serafini','Paziente');
insert into Utente values ('Gargamella','IPuffi','Fabio','Barchesi','Paziente');
insert into Utente values ('IlSole','Teletubbies','Valerio','De Angelis','Paziente');
insert into Utente values ('Mars','-','Mario','Bros','Psicologo');

--POPOLAZIONE PAZIENTE
insert into Paziente values(29,'CPTRO93H45G881W',NULL,'Ros98',NULL);
insert into Paziente values(35,'CLIRO90K45T201J','Alcolismo di secondo grado','Giacomino','Ciccio');
insert into Paziente values(35,'LGIMI67W90T631L','Depressione Acuta','Luigi@67','Mars');
insert into Paziente values(27,'PUFFI78T22J542K',NULL,'Gargamella',NULL);
insert into Paziente values(27,'SLPTT50H37J292K','Comportamento aggressivo passivo','IlSole','Mars');

--POPOLAZIONE PSICOLOGO
insert into Psicologo values(25,'Arco della Libertà','Ciccio');
insert into Psicologo values(50,'Derivazione Costante','BelPaolino');
insert into Psicologo values(120,'Il REGNO dei sogni','IlPrincipe');
insert into Psicologo values(38,'Marsolandia','Mars');

--APPUNTAMENTI PSICOLOGO

--ciccio
insert into appuntamento values('011ax','23/07/2024','15:30','Giacomino','Ciccio');
insert into appuntamento values('012ax','23/07/2024','15:30','Luigi@67','Ciccio');







