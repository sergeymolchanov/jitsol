select
    i.rowpointer,
    i.name
  from item i
 where i.rowpointer not in (0x0, 0x10)