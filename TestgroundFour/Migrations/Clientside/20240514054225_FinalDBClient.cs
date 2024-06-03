using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace TestgroundFour.Migrations.Clientside
{
    /// <inheritdoc />
    public partial class FinalDBClient : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Approvercheck",
                table: "clientdatas",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "Approvername",
                table: "clientdatas",
                type: "nvarchar(max)",
                nullable: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Approvercheck",
                table: "clientdatas");

            migrationBuilder.DropColumn(
                name: "Approvername",
                table: "clientdatas");
        }
    }
}
