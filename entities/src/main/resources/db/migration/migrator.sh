flyway migrate  -url="jdbc:postgresql://localhost:5432/tamsyv4" -user="odoo" -password="odoo" -locations="filesystem:/Users/atlantis/Documents/Encentral/shared/user-management-base/entities/src/main/resources/db/migration/default/,filesystem:./"



ocker run --name some-postgres -e POSTGRES_PASSWORD=odoo  -e POSTGRES_USER=odoo -d -e POSTGRES_DB=tamsy -p5432:5432 postgres:11