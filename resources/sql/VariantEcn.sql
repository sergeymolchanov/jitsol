select e.vptr, e.from_serial_number, e.to_serial_number, je.number prefix
  from ecnitem e
  join variants v on v.rowpointer = e.vptr
  join JIT_item je on je.rowpointer = e.serialiptr
 where e.ecn_for = 'V'
   and v.active = 1
   and v.RowPointer not in (0x0, 0x10)
   and v.iptr not in (0x0, 0x10)
 order by e.vptr