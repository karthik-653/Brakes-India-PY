
using System;
using System.ComponentModel.DataAnnotations;

namespace TestgroundFour.Models
{
	public class Unitline
	{
        [Key]
        public int Index { get; set; }

        public string? Unit{ get; set; }
        public string? Assembly { get; set; }
        public int Cell { get; set; }

    }
}

