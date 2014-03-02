package team2869.bethpage.robotics.aerialassist;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 *
 * @author BETHPAGE HIGH SCHOOL, 2014 TEAM #2869 - HARSHIL GARG
 */
public class AxisCameraM1101 {
    
    private final int Y_IMAGE_RES = RobotMap.Y_IMAGE_RES;
    private final double VIEW_ANGLE = RobotMap.VIEW_ANGLE;
    
    private final int RECTANGULARITY_LIMIT = RobotMap.RECTANGULARITY_LIMIT;
    private final int ASPECT_RATIO_LIMIT = RobotMap.ASPECT_RATIO_LIMIT;
    
    private final int TAPE_WIDTH_LIMIT = RobotMap.TAPE_WIDTH_LIMIT;
    private final int VERTICAL_SCORE_LIMIT = RobotMap.VERTICAL_SCORE_LIMIT;
    private final int LR_SCORE_LIMIT = RobotMap.LR_SCORE_LIMIT;
    
    private final int AREA_MINIMUM = RobotMap.AREA_MINIMUM;
    private final int MAX_PARTICLES = RobotMap.MAX_PARTICLES;
    
    AxisCamera camera;
    CriteriaCollection criteriaCollection;
    
    public class Scores {
        double rectangularity;
        double aspectRatioVertical;
        double aspectRatioHorizontal;
    }
    
    public class TargetReport {
        int verticalIndex;
	int horizontalIndex;
	boolean hot;
	double totalScore;		
        double leftScore;
	double rightScore;
	double tapeWidthScore;
	double verticalScore;
    }
    
    public AxisCameraM1101() {
        camera = AxisCamera.getInstance();
        criteriaCollection = new CriteriaCollection();
        criteriaCollection.addCriteria(NIVision.MeasurementType.IMAQ_MT_AREA, 
                AREA_MINIMUM, 65535, true);
    }
    
    /**
     * Calculate distance in inches. 
     * 
     * @return Distance in inches.
     */
    public double getDistance() {
        TargetReport target = new TargetReport();
        int verticalTargets[] = new int[MAX_PARTICLES];
        int horizontalTargets[] = new int[MAX_PARTICLES];
        int verticalTargetCount, horizontalTargetCount;
        
        try {
            ColorImage image = camera.getImage();
            BinaryImage thresholdImage = image.thresholdHSV(105, 137, 230, 255, 133, 183);
            BinaryImage filteredImage = thresholdImage.particleFilter(criteriaCollection);
            
            Scores scores[] = new Scores[filteredImage.getNumberParticles()];
            verticalTargetCount = 0;
            horizontalTargetCount = 0;
            
            if (filteredImage.getNumberParticles() > 0) {
                for (int i = 0; i < MAX_PARTICLES && i < filteredImage.getNumberParticles(); i++) {
                    ParticleAnalysisReport report = filteredImage.getParticleAnalysisReport(i);
                    scores[i] = new Scores();
                    
                    scores[i].rectangularity = scoreRectangularity(report);
                    scores[i].aspectRatioVertical = scoreAspectRatio(filteredImage, report, i, true);
                    scores[i].aspectRatioHorizontal = scoreAspectRatio(filteredImage, report, i, false);
                    
                    if (scoreCompare(scores[i], false)) {
                        horizontalTargets[horizontalTargetCount++] = i;
                    }
                    else if (scoreCompare(scores[i], true)) {
                        verticalTargets[verticalTargetCount++] = i;
                    }
                }
                
                target.totalScore = 0;
                target.leftScore = 0;
                target.rightScore = 0;
                target.tapeWidthScore = 0;
                target.verticalScore = 0;
                
                target.verticalIndex = verticalTargets[0];
                
                for (int j =0; j < horizontalTargetCount; j++) {
                    ParticleAnalysisReport verticalReport = filteredImage.getParticleAnalysisReport(verticalTargets[j]);
                    for (int k = 0; k < horizontalTargetCount; k++) {
                        ParticleAnalysisReport horizontalReport = filteredImage.getParticleAnalysisReport(horizontalTargets[k]);
                        
                        double horizWidth, horizHeight, vertWidth, leftScore, rightScore, tapeWidthScore, verticalScore, total;
                        
                        horizWidth = NIVision.MeasureParticle(filteredImage.image, horizontalTargets[k], false, MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
                        vertWidth = NIVision.MeasureParticle(filteredImage.image, verticalTargets[j], false, MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
                        horizHeight = NIVision.MeasureParticle(filteredImage.image, horizontalTargets[k], false, MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
                        
                        leftScore = ratioToScore(1.2*(verticalReport.boundingRectLeft - horizontalReport.center_mass_x)/horizWidth);
                        rightScore = ratioToScore(1.2 * (horizontalReport.center_mass_x - verticalReport.boundingRectLeft - verticalReport.boundingRectWidth) / horizWidth);
                        
                        tapeWidthScore = ratioToScore(vertWidth/horizHeight);
                        verticalScore = ratioToScore(1-(verticalReport.boundingRectTop - horizontalReport.center_mass_y)/(4*horizHeight));
                        
                        total = leftScore > rightScore ? leftScore:rightScore;
                        total += tapeWidthScore + verticalScore;
                        
                        if(total > target.totalScore) {
                            target.horizontalIndex = horizontalTargets[k];
                            target.verticalIndex = verticalTargets[j];
                            target.totalScore = total;
                            target.leftScore = leftScore;
                            target.rightScore = rightScore;
                            target.tapeWidthScore = tapeWidthScore;
                            target.verticalScore = verticalScore;
                        }
                    }   
                }
                if (verticalTargetCount > 0) {
                    ParticleAnalysisReport distanceReport = filteredImage.getParticleAnalysisReport(target.verticalIndex);
                    double distance = computeDistance(filteredImage, distanceReport, target.verticalIndex);
                    
                    return distance;
                }
            }
            
            filteredImage.free();
            thresholdImage.free();
            image.free();
        
        } catch (AxisCameraException ex) {
        } catch (NIVisionException ex) {
        }
        
        return 0;
    }
    
    /**
     * Calculates distance in centimeters.
     * 
     * @return The distance in centimeters.
     */
    public double getDistanceCentimeters() {
        return getDistance()*RobotMap.INCHES_CM_CONVERSION;
    }
    
    /**
     * Computes the estimated distance to a target using the height of the
     * particle in the image. For more information and graphics showing the math
     * behind this approach see the Vision Processing section of the
     * ScreenStepsLive documentation.
     *
     * @param image The image to use for measuring the particle estimated
     * rectangle.
     * @param report The particle analysis report for the particle.
     * @param outer True if the particle should be treated as an outer target,
     * false to treat it as a center target.
     * @return The estimated distance to the target in inches.
     */
    private double computeDistance(BinaryImage image, ParticleAnalysisReport 
            report, int particleNumber) throws NIVisionException {
        double rectLong, height;
        int targetHeight;

        rectLong = NIVision.MeasureParticle(image.image, particleNumber, false, 
                MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
        height = Math.min(report.boundingRectHeight, rectLong);
        targetHeight = 32;

        return Y_IMAGE_RES * targetHeight / (height * 12 * 2 
                * Math.tan(VIEW_ANGLE * Math.PI / (180 * 2)));
    }

    /**
     * Computes a score (0-100) comparing the aspect ratio to the ideal aspect
     * ratio for the target. This method uses the equivalent rectangle sides to
     * determine aspect ratio as it performs better as the target gets skewed by
     * moving to the left or right. The equivalent rectangle is the rectangle
     * with sides x and y where particle area, xy and particle perimeter, 2x+2y
     *
     * @param image The image containing the particle to score, needed to
     * perform additional measurements
     * @param report The Particle Analysis Report for the particle, used for the
     * width, height, and particle number
     * @param outer Indicates whether the particle aspect ratio should be
     * compared to the ratio for the inner target or the outer
     * @return The aspect ratio score (0-100)
     */
    private double scoreAspectRatio(BinaryImage image, ParticleAnalysisReport 
            report, int particleNumber, boolean vertical) throws 
            NIVisionException {
        
        double rectLong, rectShort, aspectRatio, idealAspectRatio;

        rectLong = NIVision.MeasureParticle(image.image, particleNumber, false, 
                MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
        rectShort = NIVision.MeasureParticle(image.image, particleNumber, false, 
                MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
        idealAspectRatio = vertical ? (4.0 / 32) : (23.5 / 4);
        
        if (report.boundingRectWidth > report.boundingRectHeight) {
            aspectRatio = ratioToScore((rectLong / rectShort)/idealAspectRatio);
        } else {
            aspectRatio = ratioToScore((rectShort / rectLong)/idealAspectRatio);
        }
        return aspectRatio;
    }

    /**
     * Compares scores to defined limits and returns true if the particle
     * appears to be a target
     *
     * @param scores The structure containing the scores to compare
     * @param vertical True if the particle should be treated as outer target,
     * false to treat it as a center target
     *
     * @return True if the particle meets all limits, false otherwise
     */
    private boolean scoreCompare(Scores scores, boolean vertical) {
        boolean isTarget = true;

        isTarget &= scores.rectangularity > RECTANGULARITY_LIMIT;
        if (vertical) {
            isTarget &= scores.aspectRatioVertical > ASPECT_RATIO_LIMIT;
        } else {
            isTarget &= scores.aspectRatioHorizontal > ASPECT_RATIO_LIMIT;
        }

        return isTarget;
    }

    /**
     * Computes a score (0-100) estimating how rectangular the particle is by
     * comparing the area of the particle to the area of the bounding box that
     * surrounds it. A perfect rectangle would cover the entire bounding box.
     *
     * @param report The particle analysis report for the particle to score.
     * @return The rectangularity score, ranging from 0 to 100.
     */
    private double scoreRectangularity(ParticleAnalysisReport report) {
        double boundArea = report.boundingRectWidth * report.boundingRectHeight;
        if (boundArea != 0) {
            return 100 * report.particleArea / boundArea;
        } else {
            return 0;
        }
    }

    /**
     * Converts a ratio with ideal value of 1 to a score. The resulting function
     * is piecewise linear going from (0,0) to (1,100) to (2,0) and is 0 for all
     * inputs outside the range 0 to 2.
     */
    private double ratioToScore(double ratio) {
        return (Math.max(0, Math.min(100 * (1 - Math.abs(1 - ratio)), 100)));
    }

    /**
     * Takes in a report on a target and compares scores to defined score limits 
     * to evaluate if the target is a hot target or not.
     * 
     * @param target The TargetReport to evaluate.
     * @return True if the target is hot, false if it is not.
     */
    private boolean hotOrNot(TargetReport target) {
        boolean isHot = true;

        isHot &= target.tapeWidthScore >= TAPE_WIDTH_LIMIT;
        isHot &= target.verticalScore >= VERTICAL_SCORE_LIMIT;
        isHot &= (target.leftScore > LR_SCORE_LIMIT) | 
                (target.rightScore > LR_SCORE_LIMIT);

        return isHot;
    }
}
