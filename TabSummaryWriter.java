/*
 * Copyright (C) 2016 captainbowtie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.allenbarr.MockTrialTabulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author captainbowtie
 */
public abstract class TabSummaryWriter {

    public static void createTabSummary(Tournament tournament, File file) {
        final Workbook wb = new XSSFWorkbook();
        final Sheet sheet = wb.createSheet("Tabulation Summary");
        //Tab summary header
        final Row tournamentNameRow = sheet.createRow(0);
        final Row hostedByRow = sheet.createRow(1);
        final Row hostSchoolRow = sheet.createRow(2);
        final Row tournamentDateRow = sheet.createRow(3);
        final Row tabulationSummaryRow = sheet.createRow(4); 
        tournamentNameRow.createCell(0).setCellValue("Tournament Name");
        hostedByRow.createCell(0).setCellValue("Hosted by");
        hostSchoolRow.createCell(0).setCellValue("Host School");
        tournamentDateRow.createCell(0).setCellValue("Tournament Date");
        tabulationSummaryRow.createCell(0).setCellValue("TABULATION SUMMARY");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 15));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 15));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 15));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 15));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 15));
        Font bold = wb.createFont();
        bold.setBold(true);
        CellStyle boldCentered = wb.createCellStyle();
        CellStyle centered = wb.createCellStyle();
        boldCentered.setAlignment(CellStyle.ALIGN_CENTER);
        boldCentered.setFont(bold);
        centered.setAlignment(CellStyle.ALIGN_CENTER);
        tournamentNameRow.getCell(0).setCellStyle(boldCentered);
        hostedByRow.getCell(0).setCellStyle(centered);
        hostSchoolRow.getCell(0).setCellStyle(centered);
        tournamentDateRow.getCell(0).setCellStyle(centered);
        tabulationSummaryRow.getCell(0).setCellStyle(boldCentered);
        int verticalOffset = 6;
        //Team data headers
        final Row headerRow  = sheet.createRow(verticalOffset-1);
        //Set header cell values
        headerRow.createCell(0).setCellValue("Team");
        headerRow.createCell(1).setCellValue("Round 1");
        headerRow.createCell(2);
        headerRow.createCell(3);
        headerRow.createCell(4).setCellValue("Round 2");
        headerRow.createCell(5);
        headerRow.createCell(6);
        headerRow.createCell(7).setCellValue("Round 3");
        headerRow.createCell(8);
        headerRow.createCell(9);
        headerRow.createCell(10).setCellValue("Round 4");
        headerRow.createCell(11);
        headerRow.createCell(12);
        headerRow.createCell(13).setCellValue("Summary");
        //Merge header cells
        sheet.addMergedRegion(new CellRangeAddress(verticalOffset-1, verticalOffset-1, 1, 3));
        sheet.addMergedRegion(new CellRangeAddress(verticalOffset-1, verticalOffset-1, 4, 6));
        sheet.addMergedRegion(new CellRangeAddress(verticalOffset-1, verticalOffset-1, 7, 9));
        sheet.addMergedRegion(new CellRangeAddress(verticalOffset-1, verticalOffset-1, 10, 12));
        sheet.addMergedRegion(new CellRangeAddress(verticalOffset-1, verticalOffset-1, 13, 15));
        //Format cells
        CellStyle headerCell = wb.createCellStyle();
        headerCell.setBorderBottom(CellStyle.BORDER_THICK);
        headerCell.setBottomBorderColor(IndexedColors.WHITE.getIndex());
        headerCell.setAlignment(CellStyle.ALIGN_CENTER);
        headerRow.getCell(0).setCellStyle(headerCell);
        headerRow.getCell(1).setCellStyle(headerCell);
        headerRow.getCell(2).setCellStyle(headerCell);
        headerRow.getCell(3).setCellStyle(headerCell);
        headerRow.getCell(4).setCellStyle(headerCell);
        headerRow.getCell(5).setCellStyle(headerCell);
        headerRow.getCell(6).setCellStyle(headerCell);
        headerRow.getCell(7).setCellStyle(headerCell);
        headerRow.getCell(8).setCellStyle(headerCell);
        headerRow.getCell(9).setCellStyle(headerCell);
        headerRow.getCell(10).setCellStyle(headerCell);
        headerRow.getCell(11).setCellStyle(headerCell);
        headerRow.getCell(12).setCellStyle(headerCell);
        headerRow.getCell(13).setCellStyle(headerCell);
        //for loop contains all team data
        for (int a = 0; a < tournament.getTeams().size(); a++) {
            final Team team = tournament.getTeam(a);
            final Row row0 = sheet.createRow(a * 3 + verticalOffset);
            final Row row1 = sheet.createRow(a * 3 + 1 + verticalOffset);
            final Row row2 = sheet.createRow(a * 3 + 2 + verticalOffset);
            //Set cell values
            row0.createCell(0).setCellValue(team.getTeamNumber());
            row1.createCell(0).setCellValue(team.getTeamName());
            if (team.isRound1Plaintiff()) {
                row0.createCell(1).setCellValue("π");
                row0.createCell(4).setCellValue("∆");
            } else {
                row0.createCell(1).setCellValue("∆");
                row0.createCell(4).setCellValue("π");
            }
            if (team.isRound3Plaintiff()) {
                row0.createCell(7).setCellValue("π");
                row0.createCell(10).setCellValue("∆");
            } else {
                row0.createCell(7).setCellValue("∆");
                row0.createCell(10).setCellValue("π");
            }
            row0.createCell(2).setCellValue("v.");
            row0.createCell(5).setCellValue("v.");
            row0.createCell(8).setCellValue("v.");
            row0.createCell(11).setCellValue("v.");
            row0.createCell(3).setCellValue(team.getRound1Opponent());
            row0.createCell(6).setCellValue(team.getRound2Opponent());
            row0.createCell(9).setCellValue(team.getRound3Opponent());
            row0.createCell(12).setCellValue(team.getRound4Opponent());
            if (team.getRound1Ballot1PD() > 0) {
                row1.createCell(1).setCellValue("W");
            } else if (team.getRound1Ballot1PD() < 0) {
                row1.createCell(1).setCellValue("L");
            } else {
                row1.createCell(1).setCellValue("T");
            }
            if (team.getRound1Ballot2PD() > 0) {
                row1.createCell(3).setCellValue("W");
            } else if (team.getRound1Ballot2PD() < 0) {
                row1.createCell(3).setCellValue("L");
            } else {
                row1.createCell(3).setCellValue("T");
            }
            if (team.getRound2Ballot1PD() > 0) {
                row1.createCell(4).setCellValue("W");
            } else if (team.getRound1Ballot1PD() < 0) {
                row1.createCell(4).setCellValue("L");
            } else {
                row1.createCell(4).setCellValue("T");
            }
            if (team.getRound2Ballot2PD() > 0) {
                row1.createCell(6).setCellValue("W");
            } else if (team.getRound1Ballot1PD() < 0) {
                row1.createCell(6).setCellValue("L");
            } else {
                row1.createCell(6).setCellValue("T");
            }
            if (team.getRound3Ballot1PD() > 0) {
                row1.createCell(7).setCellValue("W");
            } else if (team.getRound1Ballot1PD() < 0) {
                row1.createCell(7).setCellValue("L");
            } else {
                row1.createCell(7).setCellValue("T");
            }
            if (team.getRound3Ballot2PD() > 0) {
                row1.createCell(9).setCellValue("W");
            } else if (team.getRound1Ballot1PD() < 0) {
                row1.createCell(9).setCellValue("L");
            } else {
                row1.createCell(9).setCellValue("T");
            }
            if (team.getRound4Ballot1PD() > 0) {
                row1.createCell(10).setCellValue("W");
            } else if (team.getRound1Ballot1PD() < 0) {
                row1.createCell(10).setCellValue("L");
            } else {
                row1.createCell(10).setCellValue("T");
            }
            if (team.getRound4Ballot2PD() > 0) {
                row1.createCell(12).setCellValue("W");
            } else if (team.getRound1Ballot1PD() < 0) {
                row1.createCell(12).setCellValue("L");
            } else {
                row1.createCell(12).setCellValue("T");
            }
            row2.createCell(1).setCellValue(team.getRound1Ballot1PD());
            row2.createCell(3).setCellValue(team.getRound1Ballot2PD());
            row2.createCell(4).setCellValue(team.getRound2Ballot1PD());
            row2.createCell(6).setCellValue(team.getRound2Ballot2PD());
            row2.createCell(7).setCellValue(team.getRound3Ballot1PD());
            row2.createCell(9).setCellValue(team.getRound3Ballot2PD());
            row2.createCell(10).setCellValue(team.getRound4Ballot1PD());
            row2.createCell(12).setCellValue(team.getRound4Ballot2PD());
            row0.createCell(13).setCellValue(team.getWins() + " - " + team.getLoses() + " - " + team.getTies());
            row1.createCell(13).setCellValue("CS");
            //row1.createCell(14).setCellValue("OCS");
            row1.createCell(15).setCellValue("PD");
            row2.createCell(13).setCellValue(tournament.getTeamCS(team.getTeamNumber()).toString());
            //row2.createCell(14).setCellValue(tournament.getTeamOCS(team.getTeamNumber()).toString());
            row2.createCell(15).setCellValue(team.getPD());
            //Format cells
            //Team Number
            CellStyle teamNumber = wb.createCellStyle();
            teamNumber.setAlignment(CellStyle.ALIGN_LEFT);
            teamNumber.setBorderTop(CellStyle.BORDER_THICK);
            teamNumber.setTopBorderColor(IndexedColors.WHITE.getIndex());
            teamNumber.setBorderRight(CellStyle.BORDER_THICK);
            teamNumber.setRightBorderColor(IndexedColors.WHITE.getIndex());
            if (a % 2.0 == 0) {
                teamNumber.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                teamNumber.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                teamNumber.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                teamNumber.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row0.getCell(0).setCellStyle(teamNumber);
            //Team Name
            //TODO: reduce style memory footprint, see the quick-guide, indicating you don't need distinct style objects for each style
            CellStyle teamName = wb.createCellStyle();
            teamName.setAlignment(CellStyle.ALIGN_LEFT);
            teamName.setBorderRight(CellStyle.BORDER_THICK);
            teamName.setRightBorderColor(IndexedColors.WHITE.getIndex());
            if (a % 2.0 == 0) {
                teamName.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                teamName.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                teamName.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                teamName.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row1.getCell(0).setCellStyle(teamName);
            //Side indicators
            CellStyle sideIndicator = wb.createCellStyle();
            sideIndicator.setAlignment(CellStyle.ALIGN_CENTER);
            sideIndicator.setBorderTop(CellStyle.BORDER_THICK);
            sideIndicator.setTopBorderColor(IndexedColors.WHITE.getIndex());
            sideIndicator.setBorderLeft(CellStyle.BORDER_THICK);
            sideIndicator.setLeftBorderColor(IndexedColors.WHITE.getIndex());
            if (a % 2.0 == 0) {
                sideIndicator.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                sideIndicator.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                sideIndicator.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                sideIndicator.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row0.getCell(1).setCellStyle(sideIndicator);
            row0.getCell(4).setCellStyle(sideIndicator);
            row0.getCell(7).setCellStyle(sideIndicator);
            row0.getCell(10).setCellStyle(sideIndicator);
            // "v." text
            CellStyle vText = wb.createCellStyle();
            vText.setAlignment(CellStyle.ALIGN_CENTER);
            vText.setBorderTop(CellStyle.BORDER_THICK);
            vText.setTopBorderColor(IndexedColors.WHITE.getIndex());
            if (a % 2.0 == 0) {
                vText.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                vText.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                vText.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                vText.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row0.getCell(2).setCellStyle(vText);
            row0.getCell(5).setCellStyle(vText);
            row0.getCell(8).setCellStyle(vText);
            row0.getCell(11).setCellStyle(vText);
            // opposing team number
            CellStyle opposingTeamNumber = wb.createCellStyle();
            opposingTeamNumber.setAlignment(CellStyle.ALIGN_CENTER);
            opposingTeamNumber.setBorderTop(CellStyle.BORDER_THICK);
            opposingTeamNumber.setTopBorderColor(IndexedColors.WHITE.getIndex());
            opposingTeamNumber.setBorderRight(CellStyle.BORDER_THICK);
            opposingTeamNumber.setRightBorderColor(IndexedColors.WHITE.getIndex());
            if (a % 2.0 == 0) {
                opposingTeamNumber.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                opposingTeamNumber.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                opposingTeamNumber.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                opposingTeamNumber.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row0.getCell(3).setCellStyle(opposingTeamNumber);
            row0.getCell(6).setCellStyle(opposingTeamNumber);
            row0.getCell(9).setCellStyle(opposingTeamNumber);
            row0.getCell(12).setCellStyle(opposingTeamNumber);
            // Ballot 1 win loss labels
            CellStyle ballot1WLTtext = wb.createCellStyle();
            ballot1WLTtext.setAlignment(CellStyle.ALIGN_CENTER);
            ballot1WLTtext.setBorderLeft(CellStyle.BORDER_THICK);
            ballot1WLTtext.setLeftBorderColor(IndexedColors.WHITE.getIndex());
            if (a % 2.0 == 0) {
                ballot1WLTtext.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                ballot1WLTtext.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                ballot1WLTtext.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                ballot1WLTtext.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row1.getCell(1).setCellStyle(ballot1WLTtext);
            row1.getCell(4).setCellStyle(ballot1WLTtext);
            row1.getCell(7).setCellStyle(ballot1WLTtext);
            row1.getCell(10).setCellStyle(ballot1WLTtext);
            // Ballot 2 win loss labels
            CellStyle ballot2WLTtext = wb.createCellStyle();
            ballot2WLTtext.setAlignment(CellStyle.ALIGN_CENTER);
            ballot2WLTtext.setBorderRight(CellStyle.BORDER_THICK);
            ballot2WLTtext.setRightBorderColor(IndexedColors.WHITE.getIndex());
            if (a % 2.0 == 0) {
                ballot2WLTtext.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                ballot2WLTtext.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                ballot2WLTtext.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                ballot2WLTtext.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row1.getCell(3).setCellStyle(ballot2WLTtext);
            row1.getCell(6).setCellStyle(ballot2WLTtext);
            row1.getCell(9).setCellStyle(ballot2WLTtext);
            row1.getCell(12).setCellStyle(ballot2WLTtext);
            // Ballot 1 PD
            CellStyle ballot1PD = wb.createCellStyle();
            ballot1PD.setAlignment(CellStyle.ALIGN_CENTER);
            ballot1PD.setBorderLeft(CellStyle.BORDER_THICK);
            ballot1PD.setLeftBorderColor(IndexedColors.WHITE.getIndex());
            ballot1PD.setBorderBottom(CellStyle.BORDER_THICK);
            ballot1PD.setBottomBorderColor(IndexedColors.WHITE.getIndex());
            if (a % 2.0 == 0) {
                ballot1PD.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                ballot1PD.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                ballot1PD.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                ballot1PD.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row2.getCell(1).setCellStyle(ballot1PD);
            row2.getCell(4).setCellStyle(ballot1PD);
            row2.getCell(7).setCellStyle(ballot1PD);
            row2.getCell(10).setCellStyle(ballot1PD);
            // Ballot 2 PD
            CellStyle ballot2PD = wb.createCellStyle();
            ballot2PD.setAlignment(CellStyle.ALIGN_CENTER);
            ballot2PD.setBorderRight(CellStyle.BORDER_THICK);
            ballot2PD.setRightBorderColor(IndexedColors.WHITE.getIndex());
            ballot2PD.setBorderBottom(CellStyle.BORDER_THICK);
            ballot2PD.setBottomBorderColor(IndexedColors.WHITE.getIndex());
            if (a % 2.0 == 0) {
                ballot2PD.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                ballot2PD.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                ballot2PD.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                ballot2PD.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row2.getCell(3).setCellStyle(ballot2PD);
            row2.getCell(6).setCellStyle(ballot2PD);
            row2.getCell(9).setCellStyle(ballot2PD);
            row2.getCell(12).setCellStyle(ballot2PD);
            //W-L-T record text
            sheet.addMergedRegion(new CellRangeAddress(a * 3 + verticalOffset, a * 3 + verticalOffset, 13, 15));
            row0.createCell(14);
            row0.createCell(15);
            CellStyle wltRecord = wb.createCellStyle();
            DataFormat format = wb.createDataFormat();
            wltRecord.setDataFormat(format.getFormat("General"));
            wltRecord.setAlignment(CellStyle.ALIGN_CENTER);
            wltRecord.setBorderTop(CellStyle.BORDER_THICK);
            wltRecord.setTopBorderColor(IndexedColors.WHITE.getIndex());
            wltRecord.setBorderLeft(CellStyle.BORDER_THICK);
            wltRecord.setLeftBorderColor(IndexedColors.WHITE.getIndex());
            if (a % 2.0 == 0) {
                wltRecord.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                wltRecord.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                wltRecord.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                wltRecord.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row0.getCell(13).setCellStyle(wltRecord);
            row0.createCell(14).setCellStyle(wltRecord);
            row0.createCell(15).setCellStyle(wltRecord);
            //CS text
            CellStyle csText = wb.createCellStyle();
            csText.setAlignment(CellStyle.ALIGN_CENTER);
            csText.setBorderLeft(CellStyle.BORDER_THICK);
            csText.setLeftBorderColor(IndexedColors.WHITE.getIndex());
            if (a % 2.0 == 0) {
                csText.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                csText.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                csText.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                csText.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row1.getCell(13).setCellStyle(csText);
            //CS number
            CellStyle csNumber = wb.createCellStyle();
            csNumber.setAlignment(CellStyle.ALIGN_CENTER);
            csNumber.setBorderLeft(CellStyle.BORDER_THICK);
            csNumber.setLeftBorderColor(IndexedColors.WHITE.getIndex());
            csNumber.setBorderBottom(CellStyle.BORDER_THICK);
            csNumber.setBottomBorderColor(IndexedColors.WHITE.getIndex());
            if (a % 2.0 == 0) {
                csNumber.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                csNumber.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                csNumber.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                csNumber.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row2.getCell(13).setCellStyle(csNumber);
            //PD text
            CellStyle pdText = wb.createCellStyle();
            pdText.setAlignment(CellStyle.ALIGN_CENTER);
            if (a % 2.0 == 0) {
                pdText.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                pdText.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                pdText.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                pdText.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row1.getCell(15).setCellStyle(pdText);
            //PD number
            CellStyle pdNumber = wb.createCellStyle();
            pdNumber.setAlignment(CellStyle.ALIGN_CENTER);
            pdNumber.setBorderBottom(CellStyle.BORDER_THICK);
            pdNumber.setBottomBorderColor(IndexedColors.WHITE.getIndex());
            if (a % 2.0 == 0) {
                pdNumber.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
                pdNumber.setFillPattern(CellStyle.SOLID_FOREGROUND);
            } else {
                pdNumber.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                pdNumber.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }
            row2.getCell(15).setCellStyle(pdNumber);
            //Blank cells
            CellStyle blankCellCenter = wb.createCellStyle();
            if (a % 2.0 == 0) {
                blankCellCenter.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            } else {
                blankCellCenter.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            }
            blankCellCenter.setFillPattern(CellStyle.SOLID_FOREGROUND);
            row1.createCell(2).setCellStyle(blankCellCenter);
            row1.createCell(5).setCellStyle(blankCellCenter);
            row1.createCell(8).setCellStyle(blankCellCenter);
            row1.createCell(11).setCellStyle(blankCellCenter);
            row1.createCell(14).setCellStyle(blankCellCenter);
            
            CellStyle blankCellBottom = wb.createCellStyle();
            if (a % 2.0 == 0) {
                blankCellBottom.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            } else {
                blankCellBottom.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            }
            blankCellBottom.setFillPattern(CellStyle.SOLID_FOREGROUND);
            blankCellBottom.setBorderBottom(CellStyle.BORDER_THICK);
            blankCellBottom.setBottomBorderColor(IndexedColors.WHITE.getIndex());
            row2.createCell(0).setCellStyle(blankCellBottom);
            row2.createCell(2).setCellStyle(blankCellBottom);
            row2.createCell(5).setCellStyle(blankCellBottom);
            row2.createCell(8).setCellStyle(blankCellBottom);
            row2.createCell(11).setCellStyle(blankCellBottom);
            row2.createCell(14).setCellStyle(blankCellBottom);
        }
        sheet.autoSizeColumn(0);
        sheet.setColumnWidth(1, 1323);
        sheet.autoSizeColumn(2);
        sheet.setColumnWidth(3, 1323);
        sheet.setColumnWidth(4, 1323);
        sheet.autoSizeColumn(5);
        sheet.setColumnWidth(6, 1323);
        sheet.setColumnWidth(7, 1323);
        sheet.autoSizeColumn(8);
        sheet.setColumnWidth(9, 1323);
        sheet.setColumnWidth(10, 1323);
        sheet.autoSizeColumn(11);
        sheet.setColumnWidth(12, 1323);
        sheet.autoSizeColumn(13);
        sheet.setColumnWidth(14, 1);
        sheet.autoSizeColumn(15);
        //Team Placements
        int teamPlacementStart = tournament.getTeams().size()*3+7;
        ArrayList<Team> rankedTeams = tournament.sortTeams(tournament.getTeams());
        for(int a = 0;a<rankedTeams.size();a++){
            Row rankRow = sheet.createRow(teamPlacementStart+a);
            Team team = rankedTeams.get(a);
            String cs = tournament.getTeamCS(team.getTeamNumber()).toString();
            String rankText = a+1+") "+ team.getTeamNumber() + " "+ team.getTeamName() + " "+
                    team.getWins()+"-"+team.getLoses()+"-"+team.getTies() + " CS: "+
                    tournament.getTeamCS(team.getTeamNumber())+" PD: "+ team.getPD();
            rankRow.createCell(0).setCellValue(rankText);
        }

        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(file);
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TabSummaryWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TabSummaryWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
