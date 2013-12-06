#!/bin/bash


SQL=$( cat <<EOF
DROP DATABASE janpix_rup;
DROP DATABASE janpix_rup_prod;
DROP DATABASE janpix_regdoc_prod;
DROP USER 'janpix_rup_prod'@'localhost';
DROP USER 'janpix_rup'@'localhost';
EOF
)

echo "$SQL" | mysql -u root -p;
