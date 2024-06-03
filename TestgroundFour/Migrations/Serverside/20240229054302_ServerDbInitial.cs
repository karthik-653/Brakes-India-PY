using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace TestgroundFour.Migrations.Serverside
{
    /// <inheritdoc />
    public partial class ServerDbInitial : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "serverdatas",
                columns: table => new
                {
                    Plant = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Unit = table.Column<int>(type: "int", nullable: false),
                    Celltype = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Cellno = table.Column<int>(type: "int", nullable: false),
                    Model = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Docno = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Process = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Pyorder = table.Column<int>(type: "int", nullable: false),
                    Pyno = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Pydesc = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Pypurpose = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Pytype = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_serverdatas", x => new { x.Plant, x.Unit, x.Celltype, x.Cellno, x.Model, x.Docno, x.Process, x.Pyorder, x.Pyno });
                });
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "serverdatas");
        }
    }
}
