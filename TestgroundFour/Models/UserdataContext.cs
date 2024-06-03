using System;
using Microsoft.EntityFrameworkCore;
namespace TestgroundFour.Models
{
	public class UserdataContext : DbContext
	{
		public UserdataContext(DbContextOptions<UserdataContext> options) : base(options)
		{
		}

		public DbSet<Userdata> datas { get; set; }
	}
}

