create table if not exists spring_migration.task
(
    id       bigserial primary key,
    title    varchar,
    priority varchar
);

