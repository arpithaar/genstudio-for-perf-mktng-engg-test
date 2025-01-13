// reportWebVitals.js
import {onCLS, onFID, onINP, onLCP, onTTFB} from 'web-vitals';

// This function will log Web Vitals metrics to the console or send them to an analytics service
const reportWebVitals = (metric) => {
  console.log(metric);  // Log metric to the console
};

onCLS(reportWebVitals); // Cumulative Layout Shift
onFID(reportWebVitals); // First Input Delay
onLCP(reportWebVitals); // Largest Contentful Paint
onINP(reportWebVitals); //Interaction to Next Paint
onTTFB(reportWebVitals); //Time to First Byte

export default reportWebVitals;