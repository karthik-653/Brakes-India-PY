using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace TestgroundFour.Migrations.Unitline
{
    /// <inheritdoc />
    public partial class UnitlineDBInitial : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropPrimaryKey(
                name: "PK_Unitlinedatas",
                table: "Unitlinedatas");

            migrationBuilder.RenameTable(
                name: "Unitlinedatas",
                newName: "newUnitlinedatas");

            migrationBuilder.AddPrimaryKey(
                name: "PK_newUnitlinedatas",
                table: "newUnitlinedatas",
                column: "Index");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropPrimaryKey(
                name: "PK_newUnitlinedatas",
                table: "newUnitlinedatas");

            migrationBuilder.RenameTable(
                name: "newUnitlinedatas",
                newName: "Unitlinedatas");

            migrationBuilder.AddPrimaryKey(
                name: "PK_Unitlinedatas",
                table: "Unitlinedatas",
                column: "Index");
        }
    }
}
