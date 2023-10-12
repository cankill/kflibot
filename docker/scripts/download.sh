#!/bin/bash

declare -a files=("lib.libavtor.sql.gz"
                  "lib.libtranslator.sql.gz"
                  "lib.libavtorname.sql.gz"
                  "lib.libbook.sql.gz"
                  "lib.libfilename.sql.gz"
                  "lib.libgenre.sql.gz"
                  "lib.libgenrelist.sql.gz"
                  "lib.libjoinedbooks.sql.gz"
                  "lib.librate.sql.gz"
                  "lib.librecs.sql.gz"
                  "lib.libseqname.sql.gz"
                  "lib.libseq.sql.gz")

for name in "${files[@]}"
do
  curl https://flibusta.site/sql/$name --output /docker-entrypoint-initdb.d/$name
  gunzip -f /docker-entrypoint-initdb.d/$name
done