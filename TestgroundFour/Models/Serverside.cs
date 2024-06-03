using System;
using System.ComponentModel.DataAnnotations;

namespace TestgroundFour.Models
{
	public class Serverside
	{

        //the first 9 columns combine to form a key
        public string? Plant { get; set; }
        
        public int Unit { get; set; }
        
        public string? Celltype { get; set; }
     
        public int Cellno { get; set; }
        
        public string? Model { get; set; }
        
        public string? Docno { get; set; }
  
        public string? Process { get; set; }

        public int Pyorder { get; set; }
        
        public string? Pyno { get; set; }

        public string? Pydesc { get; set; }
        public string? Pypurpose { get; set; }
        public string? Pytype { get; set; }
    }
}

