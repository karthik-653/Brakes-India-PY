using TestgroundFour.Models;
using Microsoft.EntityFrameworkCore;
var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddDbContext<UserdataContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("UserdataCS")));

builder.Services.AddDbContext<UnitlineContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("UnitlineCS")));

builder.Services.AddDbContext<ServersideContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("ServersideCS")));

builder.Services.AddDbContext<ClientsideContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("ClientsideCS")));

builder.Services.AddDbContext<ApproversideContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("ApproversideCS")));

builder.Services.AddControllers();


// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseAuthorization();

app.MapControllers();

app.Run();

