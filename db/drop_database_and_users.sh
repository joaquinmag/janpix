#!/bin/bash

echo "Drop de bases de datos mysql";

SQL=$( cat <<EOF
DROP DATABASE janpix_rup;
DROP DATABASE janpix_rup_prod;
DROP DATABASE janpix_regdoc_prod;
DROP USER 'janpix_rup_prod'@'localhost';
DROP USER 'janpix_rup'@'localhost';
EOF
)

echo "$SQL" | mysql -u root -p;

echo "Drop de mongodb de janpix_repodoc_prod";
JS=$( cat <<EOF
use janpix_repodoc_prod;
db.dropDatabase();
EOF
)
echo "$JS" | mongo
echo "Drop de mongodb de janpix_repodoc_test";
JS=$( cat <<EOF
use janpix_repodoc_test;
db.dropDatabase();
EOF
)
echo "$JS" | mongo
