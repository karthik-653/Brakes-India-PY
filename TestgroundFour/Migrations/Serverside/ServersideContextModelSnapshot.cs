﻿// <auto-generated />
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using TestgroundFour.Models;

#nullable disable

namespace TestgroundFour.Migrations.Serverside
{
    [DbContext(typeof(ServersideContext))]
    partial class ServersideContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "8.0.1")
                .HasAnnotation("Relational:MaxIdentifierLength", 128);

            SqlServerModelBuilderExtensions.UseIdentityColumns(modelBuilder);

            modelBuilder.Entity("TestgroundFour.Models.Serverside", b =>
                {
                    b.Property<string>("Plant")
                        .HasColumnType("nvarchar(450)");

                    b.Property<int>("Unit")
                        .HasColumnType("int");

                    b.Property<string>("Celltype")
                        .HasColumnType("nvarchar(450)");

                    b.Property<int>("Cellno")
                        .HasColumnType("int");

                    b.Property<string>("Model")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("Docno")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("Process")
                        .HasColumnType("nvarchar(450)");

                    b.Property<int>("Pyorder")
                        .HasColumnType("int");

                    b.Property<string>("Pyno")
                        .HasColumnType("nvarchar(450)");

                    b.Property<string>("Pydesc")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Pypurpose")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Pytype")
                        .HasColumnType("nvarchar(max)");

                    b.HasKey("Plant", "Unit", "Celltype", "Cellno", "Model", "Docno", "Process", "Pyorder", "Pyno");

                    b.ToTable("serverdatas");
                });
#pragma warning restore 612, 618
        }
    }
}
