using System;
using Microsoft.EntityFrameworkCore;

namespace TestgroundFour.Models
{

        public class ClientsideContext : DbContext
        {
            public ClientsideContext(DbContextOptions<ClientsideContext> options) : base(options)
            {
            }

            protected override void OnModelCreating(ModelBuilder modelBuilder)
            {
                modelBuilder.Entity<Clientside>()
        .HasKey(e => new { e.Plant, e.Unit, e.Celltype, e.Cellno, e.Model, e.Docno, e.Process, e.Pyorder, e.Pyno, e.Date });

            }

            public DbSet<Clientside> clientdatas { get; set; }

        }
}

