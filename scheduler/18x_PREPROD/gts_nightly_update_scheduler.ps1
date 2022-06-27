Get-Date | Out-File -Append G:\LNIntegration\LNTransfer\gts_nightly_update.log
Write-Host "Creating .lock file"
"Creating .lock file" | Out-File -Append G:\LNIntegration\LNTransfer\gts_nightly_update.log
New-Item -Path 'G:\LNIntegration\LNTransfer\nightly_update_scheduler.lock' -ItemType File
Invoke-WebRequest -Uri 'https://dsppspace.plm.valmet.com:443/EnoviaRestService/gts-services/nightlyUpdatesLN' | Out-File -Append G:\LNIntegration\LNTransfer\gts_nightly_update.log
Start-Sleep -s 15
Remove-Item -Path 'G:\LNIntegration\LNTransfer\nightly_update_scheduler.lock'
Get-Date | Out-File -Append G:\LNIntegration\LNTransfer\gts_nightly_update.log