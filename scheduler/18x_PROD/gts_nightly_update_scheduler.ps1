Get-Date | Out-File -Append G:\integrations\PROD\LNTransfer\gts_nightly_update.log
Write-Host "Creating .lock file"
"Creating .lock file" | Out-File -Append G:\integrations\PROD\LNTransfer\gts_nightly_update.log
New-Item -Path 'G:\integrations\PROD\LNTransfer\nightly_update_scheduler.lock' -ItemType File
Invoke-WebRequest -Uri 'https://dsprspace.plm.valmet.com:443/EnoviaRestService/gts-services/nightlyUpdatesLN' | Out-File -Append G:\integrations\PROD\LNTransfer\gts_nightly_update.log
Start-Sleep -s 15
Remove-Item -Path 'G:\integrations\PROD\LNTransfer\nightly_update_scheduler.lock'
Get-Date | Out-File -Append G:\integrations\PROD\LNTransfer\gts_nightly_update.log
