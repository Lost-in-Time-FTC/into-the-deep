/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.config.Hardware;

@TeleOp(name="Iterative TeleOp", group="Iterative OpMode")
public class IterativeTeleOp extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Hardware hardware;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        hardware = new Hardware(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @Override
    public void init_loop() {

    }

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    @Override
    public void loop() {
        drive();

        // Show the elapsed game time
        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

    public void drive() {
        // Mecanum
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x;

        // Strafing
        double fL = Range.clip(drive + strafe + turn, -0.5, 0.5);
        double fR = Range.clip(drive - strafe - turn, -0.5, 0.5);
        double bL = Range.clip(drive - strafe + turn, -0.5, 0.5);
        double bR = Range.clip(drive + strafe - turn, -0.5, 0.5);

        double rapidMode = 1.75;
        double sniperMode = 0.25;

        // Sniper mode
        if (gamepad1.left_trigger > 0) {
            hardware.fL.setPower(fL * rapidMode * sniperMode);
            hardware.fR.setPower(fR * rapidMode * sniperMode);
            hardware.bL.setPower(bL * rapidMode * sniperMode);
            hardware.bR.setPower(bR * rapidMode * sniperMode);
        }
        // Brakes
        else if (gamepad1.right_trigger > 0) {
            hardware.fL.setPower(fL * 0);
            hardware.fR.setPower(fR * 0);
            hardware.bL.setPower(bL * 0);
            hardware.bR.setPower(bR * 0);

        }
        // Normal drive
        else {
            hardware.fL.setPower(fL * rapidMode);
            hardware.fR.setPower(fR * rapidMode);
            hardware.bL.setPower(bL * rapidMode);
            hardware.bR.setPower(bR * rapidMode);
        }
    }
}
