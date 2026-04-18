package com.bookmyshow.dto.response;

import java.math.BigDecimal;
import java.util.List; public class AdminDashboardResponse {
    private long totalMovies;

    private long totalTheatres;

    private long totalBookings;

    private BigDecimal totalRevenue;

    private List<BookingResponse> recentBookings;

    // === Generated boilerplate (Lombok removed for JDK 25 compatibility) ===

    public long getTotalMovies() { return this.totalMovies; }
    public long getTotalTheatres() { return this.totalTheatres; }
    public long getTotalBookings() { return this.totalBookings; }
    public BigDecimal getTotalRevenue() { return this.totalRevenue; }
    public List<BookingResponse> getRecentBookings() { return this.recentBookings; }

    public void setTotalMovies(long totalMovies) { this.totalMovies = totalMovies; }
    public void setTotalTheatres(long totalTheatres) { this.totalTheatres = totalTheatres; }
    public void setTotalBookings(long totalBookings) { this.totalBookings = totalBookings; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    public void setRecentBookings(List<BookingResponse> recentBookings) { this.recentBookings = recentBookings; }

    public AdminDashboardResponse() {}

    public AdminDashboardResponse(long totalMovies, long totalTheatres, long totalBookings, BigDecimal totalRevenue, List<BookingResponse> recentBookings) {
        this.totalMovies = totalMovies;
        this.totalTheatres = totalTheatres;
        this.totalBookings = totalBookings;
        this.totalRevenue = totalRevenue;
        this.recentBookings = recentBookings;
    }

    public static  AdminDashboardResponseBuilder builder() { return new AdminDashboardResponseBuilder(); }

    public static class AdminDashboardResponseBuilder {
        private long totalMovies;
        private long totalTheatres;
        private long totalBookings;
        private BigDecimal totalRevenue;
        private List<BookingResponse> recentBookings;

        public AdminDashboardResponseBuilder totalMovies(long totalMovies) { this.totalMovies = totalMovies; return this; }
        public AdminDashboardResponseBuilder totalTheatres(long totalTheatres) { this.totalTheatres = totalTheatres; return this; }
        public AdminDashboardResponseBuilder totalBookings(long totalBookings) { this.totalBookings = totalBookings; return this; }
        public AdminDashboardResponseBuilder totalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; return this; }
        public AdminDashboardResponseBuilder recentBookings(List<BookingResponse> recentBookings) { this.recentBookings = recentBookings; return this; }

        public AdminDashboardResponse build() { return new AdminDashboardResponse(totalMovies, totalTheatres, totalBookings, totalRevenue, recentBookings); }
    }

}
