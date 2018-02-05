select
	js.rowpointer,
	js.vptr,
	js.Number number,
	js.descr
  from jobstep js
 where js.obsolete = 0
   and js.rowpointer != 0x0
 order by js.iptr, js.vptr, js.Number