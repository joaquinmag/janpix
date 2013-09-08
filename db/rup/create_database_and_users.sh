#!/bin/bash

SQL=$( cat <<EOF
CREATE DATABASE janpix_rup DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE USER 'janpix_rup'@'localhost' IDENTIFIED BY 'janpix_rup';
GRANT ALL ON janpix_rup.* TO 'janpix_rup'@'localhost';
CREATE DATABASE janpix_rup_prod DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE USER 'janpix_rup_prod'@'localhost' IDENTIFIED BY 'janpix_rup_prod';
GRANT ALL ON janpix_rup_prod.* TO 'janpix_rup_prod'@'localhost';
EOF
)

echo "$SQL" | mysql -u root -p;