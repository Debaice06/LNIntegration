if ((Get-ChildItem  D:\Integrations\LNTransfer\ -Filter *.xml | Select-Object -Property fullname) -and !(Get-ChildItem  D:\Integrations\LNTransfer\ -Filter *.lock | Select-Object -Property fullname))
{
Get-Date | Out-File -Append D:\Integrations\LNTransfer\Ln_Scheduler.log
Write-Host "Creating .lock file"
"Creating .lock file" | Out-File -Append D:\Integrations\LNTransfer\Ln_Scheduler.log
New-Item -Path 'D:\Integrations\LNTransfer\scheduler.lock' -ItemType File
Invoke-WebRequest -Uri 'https://dst1space.plm.valmet.com/EnoviaRestService/bomExportToLN' | Out-File -Append D:\Integrations\LNTransfer\Ln_Scheduler.log
#Start-Sleep -s 15
#Remove-Item -Path 'D:\Integrations\LNTransfer\scheduler.lock'
Get-Date | Out-File -Append D:\Integrations\LNTransfer\Ln_Scheduler.log
}
else
{
if ((Get-ChildItem  D:\Integrations\LNTransfer\ -Filter *.lock | Select-Object -Property fullname))
{
Get-Date | Out-File -Append D:\Integrations\LNTransfer\Ln_Scheduler.log
Write-Host "Lock file is found. Previous file process is still running"
"Lock file is found. Previous file process is still running" | Out-File -Append D:\Integrations\LNTransfer\Ln_Scheduler.log
Get-Date | Out-File -Append D:\Integrations\LNTransfer\Ln_Scheduler.log
 
}
else
{
Get-Date | Out-File -Append D:\Integrations\LNTransfer\Ln_Scheduler.log
Write-Host "No files Found" 
"No files Found" | Out-File -Append D:\Integrations\LNTransfer\Ln_Scheduler.log
Get-Date | Out-File -Append D:\Integrations\LNTransfer\Ln_Scheduler.log
}
}