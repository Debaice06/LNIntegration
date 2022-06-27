Get-Date | Out-File -Append H:\Integrations\LNTransfer\gts_nightly_update_scheduler.log
Write-Host "Creating .lock file"
"Creating .lock file" | Out-File -Append H:\Integrations\LNTransfer\gts_nightly_update_scheduler.log
New-Item -Path 'H:\Integrations\LNTransfer\nightly_update_scheduler.lock' -ItemType File
Invoke-WebRequest -Uri 'https://dspr21integration.plm.valmet.com/EnoviaRestService/gts-services/nightlyUpdatesLN' | Out-File -Append H:\Integrations\LNTransfer\gts_nightly_update_scheduler.log
Start-Sleep -s 15
Remove-Item -Path 'H:\Integrations\LNTransfer\nightly_update_scheduler.lock'
Get-Date | Out-File -Append H:\Integrations\LNTransfer\gts_nightly_update_scheduler.log