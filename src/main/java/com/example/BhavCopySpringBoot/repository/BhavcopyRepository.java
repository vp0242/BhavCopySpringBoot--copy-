package com.example.BhavCopySpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

import com.example.BhavCopySpringBoot.model.BhavcopyModel;

public interface BhavcopyRepository extends JpaRepository<BhavcopyModel, Long> {

    @Query("SELECT b FROM BhavcopyModel b WHERE b.symbol = ?1")
    List<BhavcopyModel> findBySymbol(String symbol);

    @Query("SELECT count(b) FROM BhavcopyModel b WHERE b.series = ?1")
    Integer getCountBySeries(String series);

    @Query("SELECT b.symbol FROM BhavcopyModel b WHERE ((b.close -b.open)/b.open)*100 > ?1")
    List<String> getSymbolWithGain(Double gain);

    @Query("SELECT b.symbol FROM BhavcopyModel b WHERE ((b.high -b.low)/b.low)*100 > ?1")
    List<String> getSymbolWithTopBot(Double gain);

    @Query("SELECT STDDEV_POP(b.close) FROM BhavcopyModel b WHERE b.series=?1")
    Double getSTDevWthSeries(String series);

    @Query("SELECT b.symbol FROM BhavcopyModel b ORDER BY ((b.close -b.open)/b.open) * 100 DESC LIMIT ?1")
    List<String> getSymbolWithMaxGain(Integer number);

    @Query("SELECT b.symbol FROM BhavcopyModel b ORDER BY ((b.close -b.open)/b.open) * 100 LIMIT ?1")
    List<String> getSymbolWithMinGain(Integer number);

    @Query("SELECT b.symbol FROM BhavcopyModel b ORDER BY b.totalTradedQty DESC LIMIT ?1")
    List<String> getSymbolWithMaxTraded(Integer number);

    @Query("SELECT b.symbol FROM BhavcopyModel b ORDER BY b.totalTradedQty LIMIT ?1")
    List<String> getSymbolWithMinTraded(Integer number);

    @Query("SELECT b.symbol FROM BhavcopyModel b ORDER BY b.totalTradedVal DESC LIMIT 1")
    String getSymbolWithMaxTradedBySeries(String series);

    @Query("SELECT b.symbol FROM BhavcopyModel b ORDER BY b.totalTradedVal LIMIT 1")
    String getSymbolWithMinTradedBySeries(String series);

    // function for all Apis
    @Query(value = "SELECT * FROM get_bhavcopy_by_symbol(:symbol)", nativeQuery = true)
    List<BhavcopyModel> findBySymbolFunc(@Param("symbol") String symbol);

    @Query("SELECT get_count_by_series(?1)")
    Integer getCountBySeriesFunc(String series);

    @Query("SELECT get_symbols_with_gain(?1)")
    List<String> getSymbolWithGainFunc(Double gain);

    @Query("SELECT get_symbols_with_top_bot(?1)")
    List<String> getSymbolWithTopBotFunc(Double gain);

    @Query("SELECT get_stddev_by_series(?1)")
    Double getSTDevWthSeriesFunc(String series);

    @Query("SELECT get_symbols_with_max_gain(?1)")
    List<String> getSymbolWithMaxGainFunc(Integer number);

    @Query("SELECT get_symbols_with_min_gain(?1) ")
    List<String> getSymbolWithMinGainFunc(Integer number);

    @Query("SELECT get_symbols_with_max_traded(?1)")
    List<String> getSymbolWithMaxTradedFunc(Integer number);

    @Query("SELECT get_symbols_with_min_traded(?1)")
    List<String> getSymbolWithMinTradedFunc(Integer number);

    @Query("SELECT get_symbol_with_max_traded_val_by_series(?1)")
    String getSymbolWithMaxTradedBySeriesFunc(String series);

    @Query("SELECT get_symbol_with_min_traded_val_by_series(?1)")
    String getSymbolWithMinTradedBySeriesFunc(String series);

    @Query("SELECT get_Bhavcopy_Operations_Output(:operation,:inputparam)")
    List<BhavcopyModel> getInputForOperation(@Param("operation") String operation, @Param("inputparam") String inputparam);
}
