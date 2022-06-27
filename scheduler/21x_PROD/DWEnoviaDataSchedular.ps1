Get-Date | Out-File -Append H:\Integrations\LNTransfer\DW_Enovia_Data_Scheduler.log
Invoke-WebRequest -Uri 'https://dspr21integration.plm.valmet.com/EnoviaRestService/export/v1/ln/items/nightly/dw-enovia' | Out-File -Append H:\Integrations\LNTransfer\DW_Enovia_Data_Scheduler.log
Get-Date | Out-File -Append H:\Integrations\LNTransfer\DW_Enovia_Data_Scheduler.log