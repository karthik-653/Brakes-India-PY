using System;
using Microsoft.EntityFrameworkCore;


namespace TestgroundFour.Models
{
	public class ApproversideContext : DbContext
	{
		public ApproversideContext(DbContextOptions<ApproversideContext> options) : base(options)
		{
		}

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Approverside>()
    .HasKey(e => new { e.Plant, e.Unit, e.Celltype, e.Cellno, e.Model, e.Docno, e.Process, e.Pyorder, e.Pyno, e.Date });

        }
        public DbSet<Approverside> approverdatas { get; set; }


    }
}

