BEGIN TRANSACTION;

\! echo "Inserting sample data (not for production!)"

INSERT INTO VolunteersService.Users (Login, Email, Name, Surname, RegisterDate, PasswdHash1, PasswdHash2, RoleID) values
    ('admin', 'admin@email.com', 'Кристина', 'Шараева', '2020-03-26 12:20:00.000+03', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '21232f297a57a5a743894a0e4a801fc3', 4),
    ('manager', 'manager@email.com', 'Алена', 'Тарасова', '2020-03-26 12:30:00.000+03', '6ee4a469cd4e91053847f5d3fcb61dbcc91e8f0ef10be7748da4c4a1ba382d17', '1d0258c2440a8d19e716292b231e3190', 2),
    ('coord1', 'coord1@email.com', 'Анастасия', 'Брунова', '2020-03-26 12:40:00.000+03', 'ae0579039b084e7039a406df4974d5e675cf95e7118ed1865e84e571af7f0523', '181554063b829ec3aa4e38d61205dc49', 3),
    ('coord2', 'coord2@email.com', 'Екатерина', 'Кузнецова', '2020-03-26 12:50:00.000+03', '5c4c14ac91d2113e385a73496cc11922852f78c5aea00092b9fc4eae885d4ed4', 'efddd90d058791746bb8b8e6892465c9', 3),
    ('movementLeader', 'leader@email.com', 'Виктория', 'Васильева', '2020-03-26 12:30:00.000+03', '6ee4a469cd4e91053847f5d3fcb61dbcc91e8f0ef10be7748da4c4a1ba382d17', '1d0258c2440a8d19e716292b231e3190', 5);

END;