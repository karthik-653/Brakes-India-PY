using System;
using System.Linq;
using Microsoft.EntityFrameworkCore;

namespace TestgroundFour.Models
{
	public class ServersideContext : DbContext
	{
        public ServersideContext(DbContextOptions<ServersideContext> options) : base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Serverside>()
    .HasKey(e => new { e.Plant, e.Unit, e.Celltype, e.Cellno, e.Model, e.Docno, e.Process, e.Pyorder, e.Pyno });

        }

        public DbSet<Serverside> serverdatas { get; set; }

    }
}

