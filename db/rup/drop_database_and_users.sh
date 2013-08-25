#!/bin/bash


SQL=$( cat <<EOF
DROP DATABASE janpix_rup;
DROP USER 'janpix_rup'@'localhost';
DROP DATABASE janpix_rup_prod;
DROP USER 'janpix_rup_prod'@'localhost';
EOF
)

echo "$SQL" | mysql -u root -p;
