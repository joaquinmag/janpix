#!/bin/bash

echo "Creo base de datos mysql";

SQL=$( cat <<EOF
CREATE DATABASE janpix_regdoc_prod DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE janpix_rup_prod DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE USER 'janpix_rup_prod'@'localhost' IDENTIFIED BY 'janpix_rup_prod';
GRANT ALL ON janpix_rup_prod.* TO 'janpix_rup_prod'@'localhost';
GRANT ALL ON janpix_regdoc_prod.* TO 'janpix_rup_prod'@'localhost';

CREATE DATABASE janpix_rup DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE USER 'janpix_rup'@'localhost' IDENTIFIED BY 'janpix_rup';
GRANT ALL ON janpix_rup.* TO 'janpix_rup'@'localhost';
EOF
)

echo "$SQL" | mysql -u root -p;

echo "Creo bases de datos mongo";
JS=$( cat <<EOF
use janpix_repodoc_prod;
use janpix_repodoc_test;
EOF
)
echo "$JS" | mongo
