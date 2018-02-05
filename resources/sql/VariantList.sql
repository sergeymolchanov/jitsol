select
	v.RowPointer vptr,
	v.iptr,
	v.name,
	v.descr
  from variants v
 where v.active = 1
   and v.RowPointer not in (0x0, 0x10)
   and v.iptr not in (0x0, 0x10)
 order by v.iptr, v.RowPointer