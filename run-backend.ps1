if (Test-Path .env) {
    Get-Content .env | ForEach-Object {
        if ($_ -match '^\s*#' -or [string]::IsNullOrWhiteSpace($_)) { return }
        $name, $value = $_ -split '=', 2
        if (-not $name) { return }
        $trimmedName = $name.Trim()
        $trimmedValue = $value.Trim()
        Set-Item -Path "Env:$trimmedName" -Value $trimmedValue
    }
}

if (-not $env:DB_USERNAME -or -not $env:DB_PASSWORD) {
    Write-Host "No se encontraron DB_USERNAME/DB_PASSWORD en el entorno ni en .env."
    Write-Host "Crea un archivo .env con:"
    Write-Host "DB_USERNAME=postgres"
    Write-Host "DB_PASSWORD=tu_password_local"
    exit 1
}

./mvnw.cmd spring-boot:run
