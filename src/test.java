
By getChanges = By.xpath("//td[contains(@class,'blob-code blob-code-addition') or contains(@class,'blob-code blob-code-deletion')]");
		
					List<WebElement> listChanges = driver.findElements(getChanges);
			
					for (int count = 0; count < listChanges.size(); count++) {
						
						String codeChanges=driver.findElements(getChanges).get(count).getText();
							
						outputHandle.write(codeChanges);
						
						outputHandle.write("\n");