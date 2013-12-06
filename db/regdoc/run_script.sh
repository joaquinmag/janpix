#!/bin/bash

if [ $# -ne 1 ];
then
	echo usage: $0 sql-file
	exit 1
fi

mysql -u janpix_rup_prod -p janpix_regdoc_prod < $1
