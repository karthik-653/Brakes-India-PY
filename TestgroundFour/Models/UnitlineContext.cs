using System;
using Microsoft.EntityFrameworkCore;
namespace TestgroundFour.Models
{
	public class UnitlineContext : DbContext
	{
        public UnitlineContext(DbContextOptions<UnitlineContext> options) : base(options)
        {
        }

        public DbSet<Unitline> Unitlinedatas { get; set; }
    }
}

