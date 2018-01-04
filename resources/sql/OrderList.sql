select	pd.rowpointer, 
		o.name + case when count(1) over (partition by o.rowpointer) > 1 then '/' + cast(ROW_NUMBER() over (partition by o.rowpointer order by pd.linenum) as nvarchar(50)) else '' end order_name,
		pd.iptr,
		pd.qty,
		pd.demanddate,
		pd.serial_number
  from orders o
  join primdemand pd on pd.optr = o.rowpointer
 where o.internal = 0 
   and o.number is not null