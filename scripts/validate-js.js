#!/usr/bin/env node

const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

const jsDirectories = [
    'javascript',
    'examples/javascript'
];

let totalFiles = 0;
let errors = 0;

jsDirectories.forEach(dir => {
    if (fs.existsSync(dir)) {
        const files = fs.readdirSync(dir).filter(f => f.endsWith('.js'));
        files.forEach(file => {
            const filePath = path.join(dir, file);
            totalFiles++;
            try {
                execSync(`node --check "${filePath}"`, { stdio: 'pipe' });
                console.log(`✓ ${filePath}`);
            } catch (error) {
                console.error(`✗ ${filePath}: ${error.message}`);
                errors++;
            }
        });
    }
});

console.log(`\nValidated ${totalFiles} JavaScript files`);
if (errors > 0) {
    console.error(`${errors} files have syntax errors`);
    process.exit(1);
} else {
    console.log('All JavaScript files are valid');
}